import { useState, useEffect } from 'react'
import { userService } from '../services/apiService'
import { formatDate } from '../utils/helpers'
import './UserManagement.css'

function UserManagement() {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showModal, setShowModal] = useState(false)
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    organizationName: '',
    role: 'CREATOR'
  })

  useEffect(() => {
    loadUsers()
  }, [])

  const loadUsers = async () => {
    try {
      setLoading(true)
      const response = await userService.getAllUsers()
      setUsers(response.data)
      setError(null)
    } catch (err) {
      setError('Failed to load users')
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
      await userService.createUser(formData)
      setFormData({ name: '', email: '', organizationName: '', role: 'CREATOR' })
      setShowModal(false)
      loadUsers()
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to save user')
    }
  }

  if (loading) return <div className="container"><div className="loading">Loading users...</div></div>

  return (
    <div className="container">
      <div className="page-header">
        <h1>User Management</h1>
        <button onClick={() => setShowModal(true)}>+ Add User</button>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="card">
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Organization</th>
                <th>Role</th>
                <th>Created At</th>
              </tr>
            </thead>
            <tbody>
              {users.map(user => (
                <tr key={user.id}>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.organizationName || 'N/A'}</td>
                  <td><span className="badge">{user.role}</span></td>
                  <td>{formatDate(user.createdAt)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {users.length === 0 && <p className="no-data">No users found</p>}
      </div>

      {showModal && (
        <div className="modal active">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Add New User</h2>
              <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Name</label>
                <input type="text" name="name" value={formData.name} onChange={handleInputChange} required />
              </div>
              <div className="form-group">
                <label>Email</label>
                <input type="email" name="email" value={formData.email} onChange={handleInputChange} required />
              </div>
              <div className="form-group">
                <label>Organization Name</label>
                <input type="text" name="organizationName" value={formData.organizationName} onChange={handleInputChange} />
              </div>
              <div className="form-group">
                <label>Role</label>
                <select name="role" value={formData.role} onChange={handleInputChange}>
                  <option value="ADMIN">Admin</option>
                  <option value="CREATOR">Creator</option>
                  <option value="DISTRIBUTOR">Distributor</option>
                </select>
              </div>
              <button type="submit" className="btn-submit">Save User</button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default UserManagement
