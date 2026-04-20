import { useState, useEffect } from 'react'
import { royaltyService, contentService } from '../services/apiService'
import { formatDate, formatCurrency, formatPercentage } from '../utils/helpers'
import './RoyaltyCalculation.css'

function RoyaltyCalculation() {
  const [calculations, setCalculations] = useState([])
  const [payments, setPayments] = useState([])
  const [content, setContent] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [activeTab, setActiveTab] = useState('calculations')
  const [selectedContent, setSelectedContent] = useState('')

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      setLoading(true)
      const [calcRes, payRes, contentRes] = await Promise.all([
        royaltyService.getAllCalculations().catch(() => ({ data: [] })),
        royaltyService.getAllPayments().catch(() => ({ data: [] })),
        contentService.getAllContent().catch(() => ({ data: [] }))
      ])
      setCalculations(calcRes.data)
      setPayments(payRes.data)
      setContent(contentRes.data)
      setError(null)
    } catch (err) {
      setError('Failed to load data')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleCalculateRoyalty = async (contentId) => {
    try {
      await royaltyService.calculateRoyalty(contentId)
      setError(null)
      loadData()
    } catch (err) {
      setError('Failed to calculate royalty')
      console.error(err)
    }
  }

  const handleApproveCalculation = async (calcId) => {
    try {
      await royaltyService.approveCalculation(calcId)
      setError(null)
      loadData()
    } catch (err) {
      setError('Failed to approve calculation')
      console.error(err)
    }
  }

  if (loading) return <div className="container"><div className="loading">Loading royalty data...</div></div>

  return (
    <div className="container">
      <div className="page-header">
        <h1>Royalty Management</h1>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="tabs">
        <button 
          className={`tab-btn ${activeTab === 'calculations' ? 'active' : ''}`}
          onClick={() => setActiveTab('calculations')}
        >
          Calculations
        </button>
        <button 
          className={`tab-btn ${activeTab === 'payments' ? 'active' : ''}`}
          onClick={() => setActiveTab('payments')}
        >
          Payments
        </button>
        <button 
          className={`tab-btn ${activeTab === 'calculate' ? 'active' : ''}`}
          onClick={() => setActiveTab('calculate')}
        >
          Calculate New
        </button>
      </div>

      {activeTab === 'calculations' && (
        <div className="card">
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Content</th>
                  <th>Total Revenue</th>
                  <th>Royalty %</th>
                  <th>Calculated Amount</th>
                  <th>Status</th>
                  <th>Date</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {calculations.map(calc => (
                  <tr key={calc.id}>
                    <td>{calc.digitalContent?.title || 'N/A'}</td>
                    <td>{formatCurrency(calc.totalRevenue)}</td>
                    <td>{formatPercentage(calc.royaltyPercentage)}</td>
                    <td>{formatCurrency(calc.calculatedAmount)}</td>
                    <td><span className="badge">{calc.calculationStatus}</span></td>
                    <td>{formatDate(calc.calculationDate)}</td>
                    <td>
                      {calc.calculationStatus === 'CALCULATED' && (
                        <button 
                          className="btn-approve"
                          onClick={() => handleApproveCalculation(calc.id)}
                        >
                          Approve
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          {calculations.length === 0 && <p className="no-data">No calculations found</p>}
        </div>
      )}

      {activeTab === 'payments' && (
        <div className="card">
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>Calculation ID</th>
                  <th>Paid Amount</th>
                  <th>Payment Date</th>
                  <th>Reference</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {payments.map(payment => (
                  <tr key={payment.id}>
                    <td>{payment.royaltyCalculation?.id || 'N/A'}</td>
                    <td>{formatCurrency(payment.paidAmount)}</td>
                    <td>{formatDate(payment.paymentDate)}</td>
                    <td>{payment.paymentReference}</td>
                    <td><span className="badge">{payment.paymentStatus}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          {payments.length === 0 && <p className="no-data">No payments found</p>}
        </div>
      )}

      {activeTab === 'calculate' && (
        <div className="card">
          <h3>Calculate Royalty for Content</h3>
          <div className="form-group">
            <label>Select Content</label>
            <select value={selectedContent} onChange={(e) => setSelectedContent(e.target.value)}>
              <option value="">Select Content</option>
              {content.map(c => (
                <option key={c.id} value={c.id}>{c.title}</option>
              ))}
            </select>
          </div>
          <button 
            onClick={() => handleCalculateRoyalty(selectedContent)}
            disabled={!selectedContent}
            className="btn-calculate"
          >
            Calculate Royalty
          </button>
        </div>
      )}
    </div>
  )
}

export default RoyaltyCalculation
