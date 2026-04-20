import { useState, useEffect } from 'react'
import { userService, contentService, usageService, royaltyService } from '../services/apiService'
import './Dashboard.css'

function Dashboard() {
  const [stats, setStats] = useState({
    totalUsers: 0,
    totalContent: 0,
    totalTransactions: 0,
    totalRoyalties: 0
  })
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    loadDashboardData()
  }, [])

  const loadDashboardData = async () => {
    try {
      setLoading(true)
      const [users, content, usage, royalty] = await Promise.all([
        userService.getAllUsers().catch(() => ({ data: [] })),
        contentService.getAllContent().catch(() => ({ data: [] })),
        usageService.getAllTransactions().catch(() => ({ data: [] })),
        royaltyService.getAllCalculations().catch(() => ({ data: [] }))
      ])

      setStats({
        totalUsers: users.data.length || 0,
        totalContent: content.data.length || 0,
        totalTransactions: usage.data.length || 0,
        totalRoyalties: royalty.data.length || 0
      })
      setError(null)
    } catch (err) {
      setError('Failed to load dashboard data')
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return <div className="container"><div className="loading">Loading dashboard...</div></div>
  }

  return (
    <div className="container">
      <div className="dashboard-header">
        <h1>Dashboard</h1>
        <p>Digital Content Rights Management System</p>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-icon">👥</div>
          <h3>Total Users</h3>
          <div className="stat-value">{stats.totalUsers}</div>
        </div>
        <div className="stat-card">
          <div className="stat-icon">📄</div>
          <h3>Content Items</h3>
          <div className="stat-value">{stats.totalContent}</div>
        </div>
        <div className="stat-card">
          <div className="stat-icon">📊</div>
          <h3>Usage Transactions</h3>
          <div className="stat-value">{stats.totalTransactions}</div>
        </div>
        <div className="stat-card">
          <div className="stat-icon">💰</div>
          <h3>Royalty Calculations</h3>
          <div className="stat-value">{stats.totalRoyalties}</div>
        </div>
      </div>

      <div className="dashboard-features">
        <h2>Quick Access</h2>
        <div className="feature-grid">
          <a href="/users" className="feature-card">
            <div className="feature-icon">👤</div>
            <h3>User Management</h3>
            <p>Manage users, roles, and permissions</p>
          </a>
          <a href="/content" className="feature-card">
            <div className="feature-icon">📚</div>
            <h3>Content Management</h3>
            <p>Upload and manage digital content</p>
          </a>
          <a href="/usage" className="feature-card">
            <div className="feature-icon">📈</div>
            <h3>Usage Tracking</h3>
            <p>Track content usage and transactions</p>
          </a>
          <a href="/royalty" className="feature-card">
            <div className="feature-icon">💸</div>
            <h3>Royalty Management</h3>
            <p>Calculate and process royalty payments</p>
          </a>
        </div>
      </div>
    </div>
  )
}

export default Dashboard
