import { useState, useEffect } from 'react'
import { usageService, contentService, userService } from '../services/apiService'
import { formatDate, formatCurrency } from '../utils/helpers'
import './UsageTracking.css'

function UsageTracking() {
  const [transactions, setTransactions] = useState([])
  const [content, setContent] = useState([])
  const [distributors, setDistributors] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showModal, setShowModal] = useState(false)
  const [activeTab, setActiveTab] = useState('RECORDED')
  
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
  }, [activeTab])

  const loadData = async () => {
    try {
      setLoading(true)
      const [transRes, contentRes, usersRes] = await Promise.all([
        usageService.getTransactionsByStatus(activeTab).catch(() => ({ data: [] })),
        contentService.getAllContent().catch(() => ({ data: [] })),
        userService.getUsersByRole('DISTRIBUTOR').catch(() => ({ data: [] }))
      ])
      setTransactions(transRes.data)
      setContent(contentRes.data)
      setDistributors(usersRes.data)
      setError(null)
    } catch (err) {
      setError('Failed to load usage data')
    } finally {
      setLoading(false)
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      await usageService.recordUsage(formData)
      setFormData({
        digitalContentId: '',
        distributorId: '',
        usageType: 'STREAM',
        usageCount: '',
        revenueGenerated: '',
        transactionDate: new Date().toISOString().split('T')[0]
      })
      setShowModal(false)
      loadData()
    } catch (err) {
      setError('Failed to record usage')
    }
  }

  const handleVerify = async (id) => {
    try {
      await usageService.verifyTransaction(id)
      loadData()
    } catch (err) {
      setError('Failed to verify transaction')
    }
  }

  const getContentTitle = (id) => content.find(c => c.id === id)?.title || 'N/A'
  const getDistributorName = (id) => distributors.find(d => d.id === id)?.name || 'N/A'

  if (loading) return <div className="container"><div className="loading">Loading usage data...</div></div>

  return (
    <div className="container">
      <div className="page-header">
        <h1>Usage Tracking</h1>
        <button onClick={() => setShowModal(true)}>+ Record Usage</button>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="tabs">
        <button className={`tab-btn ${activeTab === 'RECORDED' ? 'active' : ''}`} onClick={() => setActiveTab('RECORDED')}>Recorded</button>
        <button className={`tab-btn ${activeTab === 'VERIFIED' ? 'active' : ''}`} onClick={() => setActiveTab('VERIFIED')}>Verified</button>
      </div>

      <div className="card">
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Content</th>
                <th>Distributor</th>
                <th>Type</th>
                <th>Count</th>
                <th>Revenue</th>
                <th>Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {transactions.map(t => (
                <tr key={t.id}>
                  <td>{getContentTitle(t.digitalContentId)}</td>
                  <td>{getDistributorName(t.distributorId)}</td>
                  <td>{t.usageType}</td>
                  <td>{t.usageCount}</td>
                  <td>{formatCurrency(t.revenueGenerated)}</td>
                  <td>{formatDate(t.transactionDate)}</td>
                  <td>
                    {t.transactionStatus === 'RECORDED' && (
                      <button className="btn-approve" onClick={() => handleVerify(t.id)}>Verify</button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {transactions.length === 0 && <p className="no-data">No transactions found for status {activeTab}</p>}
      </div>

      {showModal && (
        <div className="modal active">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Record New Usage</h2>
              <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Content</label>
                <select name="digitalContentId" value={formData.digitalContentId} onChange={handleInputChange} required>
                  <option value="">Select Content</option>
                  {content.filter(c => c.contentStatus === 'ACTIVE').map(c => (
                    <option key={c.id} value={c.id}>{c.title}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Distributor</label>
                <select name="distributorId" value={formData.distributorId} onChange={handleInputChange} required>
                  <option value="">Select Distributor</option>
                  {distributors.map(d => (
                    <option key={d.id} value={d.id}>{d.name}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Usage Type</label>
                <select name="usageType" value={formData.usageType} onChange={handleInputChange}>
                  <option value="STREAM">Stream</option>
                  <option value="DOWNLOAD">Download</option>
                  <option value="VIEW">View</option>
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
              <button type="submit" className="btn-submit">Record Usage</button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default UsageTracking
