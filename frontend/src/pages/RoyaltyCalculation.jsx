import { useState, useEffect, useContext } from 'react'
import { royaltyService, contentService, paymentService, userService } from '../services/apiService'
import { formatDate, formatCurrency, formatPercentage } from '../utils/helpers'
import { AuthContext } from '../App'
import './RoyaltyCalculation.css'

function RoyaltyCalculation() {
  const { userRole } = useContext(AuthContext)
  const [calculations, setCalculations] = useState([])
  const [payments, setPayments] = useState([])
  const [content, setContent] = useState([])
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [activeTab, setActiveTab] = useState('calculations')
  const [selectedContent, setSelectedContent] = useState('')
  
  const isAdmin = userRole === 'ADMIN'

  const [showPaymentModal, setShowPaymentModal] = useState(false)
  const [paymentFormData, setPaymentFormData] = useState({
    royaltyCalculationId: '',
    paidAmount: '',
    paymentReference: ''
  })

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      setLoading(true)
      const [calcRes, payRes, contentRes, usersRes] = await Promise.all([
        royaltyService.getAllCalculations().catch(() => ({ data: [] })),
        paymentService.getAllPayments().catch(() => ({ data: [] })),
        contentService.getAllContent().catch(() => ({ data: [] })),
        userService.getAllUsers().catch(() => ({ data: [] }))
      ])
      setCalculations(calcRes.data)
      setPayments(payRes.data)
      setContent(contentRes.data)
      setUsers(usersRes.data)
      setError(null)
    } catch (err) {
      setError('Failed to load data')
    } finally {
      setLoading(false)
    }
  }

  const handleCalculateRoyalty = async (contentId) => {
    try {
      const res = await royaltyService.calculateRoyalty(contentId)
      setCalculations(res.data)
      setError(null)
      alert('Royalty calculated for all owners!')
    } catch (err) {
      setError('Failed to calculate royalty. Ensure content has assigned rights and verified transactions.')
    }
  }

  const handleApproveCalculation = async (calcId) => {
    try {
      await royaltyService.approveCalculation(calcId)
      loadData()
      setCalculations(prev => prev.map(c => c.id === calcId ? {...c, calculationStatus: 'APPROVED'} : c))
    } catch (err) {
      setError('Failed to approve calculation')
    }
  }

  const handleOpenPayment = (calc) => {
    setPaymentFormData({
      royaltyCalculationId: calc.id,
      paidAmount: calc.calculatedAmount,
      paymentReference: 'REF-' + Math.random().toString(36).substr(2, 9).toUpperCase()
    })
    setShowPaymentModal(true)
  }

  const handlePaymentSubmit = async (e) => {
    e.preventDefault()
    try {
      await paymentService.initiatePayment(paymentFormData)
      setShowPaymentModal(false)
      loadData()
      alert('Payment initiated successfully!')
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to initiate payment')
    }
  }

  const getContentTitle = (id) => content.find(c => c.id === id)?.title || 'N/A'
  const getUserName = (id) => users.find(u => u.id === id)?.name || 'N/A'

  if (loading) return <div className="container"><div className="loading">Loading royalty data...</div></div>

  return (
    <div className="container">
      <div className="page-header">
        <h1>Royalty Management</h1>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="tabs">
        <button className={`tab-btn ${activeTab === 'calculate' ? 'active' : ''}`} onClick={() => setActiveTab('calculate')}>Calculate</button>
        <button className={`tab-btn ${activeTab === 'calculations' ? 'active' : ''}`} onClick={() => setActiveTab('calculations')}>Results</button>
        <button className={`tab-btn ${activeTab === 'payments' ? 'active' : ''}`} onClick={() => setActiveTab('payments')}>Payments</button>
      </div>

      {activeTab === 'calculate' && (
        <div className="card">
          <h3>Calculate Royalty for Content</h3>
          <p>This will process all verified transactions and split revenue among rights owners.</p>
          <div className="form-group" style={{marginTop: '20px'}}>
            <label>Select Active Content</label>
            <select value={selectedContent} onChange={(e) => setSelectedContent(e.target.value)}>
              <option value="">Select Content</option>
              {content.filter(c => c.contentStatus === 'ACTIVE').map(c => (
                <option key={c.id} value={c.id}>{c.title}</option>
              ))}
            </select>
          </div>
          {isAdmin ? (
            <button onClick={() => handleCalculateRoyalty(selectedContent)} disabled={!selectedContent} className="btn-calculate">Run Calculation</button>
          ) : (
            <p className="no-data">Only Admins can run calculations.</p>
          )}
        </div>
      )}

      {activeTab === 'calculations' && (
        <div className="card">
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Content</th>
                  <th>Owner</th>
                  <th>Revenue</th>
                  <th>Split</th>
                  <th>Amount</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {calculations.map(calc => (
                  <tr key={calc.id}>
                    <td>{getContentTitle(calc.digitalContentId)}</td>
                    <td>{getUserName(calc.rightsOwnerId)}</td>
                    <td>{formatCurrency(calc.totalRevenue)}</td>
                    <td>{formatPercentage(calc.royaltyPercentage)}</td>
                    <td>{formatCurrency(calc.calculatedAmount)}</td>
                    <td><span className={`badge badge-${calc.calculationStatus.toLowerCase()}`}>{calc.calculationStatus}</span></td>
                    <td>
                      <div className="action-buttons">
                        {calc.calculationStatus === 'PENDING' && isAdmin && (
                          <button className="btn-approve" onClick={() => handleApproveCalculation(calc.id)}>Approve</button>
                        )}
                        {calc.calculationStatus === 'APPROVED' && isAdmin && (
                          <button className="btn-edit" onClick={() => handleOpenPayment(calc)}>Pay</button>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          {calculations.length === 0 && <p className="no-data">No calculations performed in this session. Go to 'Calculate' tab.</p>}
        </div>
      )}

      {activeTab === 'payments' && (
        <div className="card">
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Calc ID</th>
                  <th>Paid Amount</th>
                  <th>Date</th>
                  <th>Reference</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {payments.map(payment => (
                  <tr key={payment.id}>
                    <td>#{payment.royaltyCalculationId}</td>
                    <td>{formatCurrency(payment.paidAmount)}</td>
                    <td>{formatDate(payment.paymentDate)}</td>
                    <td>{payment.paymentReference}</td>
                    <td><span className="badge badge-success">{payment.paymentStatus}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          {payments.length === 0 && <p className="no-data">No payments found</p>}
        </div>
      )}

      {showPaymentModal && (
        <div className="modal active">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Initiate Payout</h2>
              <button className="close-btn" onClick={() => setShowPaymentModal(false)}>×</button>
            </div>
            <form onSubmit={handlePaymentSubmit}>
              <div className="form-group">
                <label>Amount to Pay</label>
                <input type="number" step="0.01" value={paymentFormData.paidAmount} onChange={(e) => setPaymentFormData({...paymentFormData, paidAmount: e.target.value})} required />
              </div>
              <div className="form-group">
                <label>Payment Reference</label>
                <input type="text" value={paymentFormData.paymentReference} onChange={(e) => setPaymentFormData({...paymentFormData, paymentReference: e.target.value})} required />
              </div>
              <button type="submit" className="btn-submit">Confirm Payment</button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default RoyaltyCalculation
