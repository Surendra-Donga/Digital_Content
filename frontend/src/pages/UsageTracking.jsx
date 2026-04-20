import { useState, useEffect } from 'react'
import { usageService, contentService, userService } from '../services/apiService'
import { formatDate, formatCurrency } from '../utils/helpers'
import './UsageTracking.css'

function UsageTracking() {
  const [transactions, setTransactions] = useState([])
  const [content, setContent] = useState([])
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showModal, setShowModal] = useState(false)
  const [formData, setFormData] = useState({
    digitalContentId: '',
    distributorId: '',
    usageType: 'STREAM',
    usageCount: '',
    revenueGenerated: '',
    transactionDate: new Date().toISOString().split('T')[0]
  })

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      setLoading(true)
      const [txRes, contentRes, usersRes] = await Promise.all([
        usageService.getAllTransactions().catch(() => ({ data: [] })),
        contentService.getAllContent().catch(() => ({ data: [] })),
        userService.getAllUsers().catch(() => ({ data: [] }))
      ])
      setTransactions(txRes.data)
      setContent(contentRes.data)
      setUsers(usersRes.data)
      setError(null)
    } catch (err) {
      setError('Failed to load data')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: value
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      await usageService.recordUsage(formData)
      setFormData({ digitalContentId: '', distributorId: '', usageType: 'STREAM', usageCount: '', revenueGenerated: '', transactionDate: new Date().toISOString().split('T')[0] })
      setShowModal(false)
      loadData()
    } catch (err) {
      setError('Failed to record usage')
      console.error(err)
    }
  }

  if (loading) return <div className="container"><div className="loading">Loading transactions...</div></div>

  return (
    <div className="container">
      <div className="page-header">
        <h1>Usage Tracking</h1>
        <button onClick={() => setShowModal(true)}>+ Record Usage</button>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="card">
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Content</th>
                <th>Distributor</th>
                <th>Type</th>
                <th>Usage Count</th>
                <th>Revenue</th>
                <th>Status</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map(tx => (
                <tr key={tx.id}>
                  <td>{tx.digitalContent?.title || 'N/A'}</td>
                  <td>{tx.distributor?.name || 'N/A'}</td>
                  <td>{tx.usageType}</td>
                  <td>{tx.usageCount}</td>
                  <td>{formatCurrency(tx.revenueGenerated)}</td>
                  <td><span className="badge">{tx.transactionStatus}</span></td>
                  <td>{formatDate(tx.transactionDate)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {transactions.length === 0 && <p className="no-data">No transactions found</p>}
      </div>

      {showModal && (
        <div className="modal active">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Record Usage</h2>
              <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Content</label>
                <select name="digitalContentId" value={formData.digitalContentId} onChange={handleInputChange} required>
                  <option value="">Select Content</option>
                  {content.map(c => (
                    <option key={c.id} value={c.id}>{c.title}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Distributor</label>
                <select name="distributorId" value={formData.distributorId} onChange={handleInputChange} required>
                  <option value="">Select Distributor</option>
                  {users.filter(u => u.role === 'DISTRIBUTOR').map(u => (
                    <option key={u.id} value={u.id}>{u.name}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Usage Type</label>
                <select name="usageType" value={formData.usageType} onChange={handleInputChange}>
                  <option value="STREAM">Stream</option>
                  <option value="DOWNLOAD">Download</option>
                  <option value="VIEW">View</option>
                  <option value="SUBSCRIPTION_ACCESS">Subscription Access</option>
                </select>
              </div>
              <div className="form-group">
                <label>Usage Count</label>
                <input type="number" name="usageCount" value={formData.usageCount} onChange={handleInputChange} required />
              </div>
              <div className="form-group">
                <label>Revenue Generated</label>
                <input type="number" step="0.01" name="revenueGenerated" value={formData.revenueGenerated} onChange={handleInputChange} required />
              </div>
              <div className="form-group">
                <label>Transaction Date</label>
                <input type="date" name="transactionDate" value={formData.transactionDate} onChange={handleInputChange} required />
              </div>
              <button type="submit" className="btn-submit">Record Usage</button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default UsageTracking
