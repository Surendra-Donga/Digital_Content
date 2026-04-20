import { useState, useEffect, useContext } from 'react'
import { contentService, userService, contentRightsService } from '../services/apiService'
import { formatDate } from '../utils/helpers'
import { AuthContext } from '../App'
import './ContentManagement.css'

function ContentManagement() {
  const { userRole } = useContext(AuthContext)
  const [content, setContent] = useState([])
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showModal, setShowModal] = useState(false)
  const [showRightsModal, setShowRightsModal] = useState(false)
  const [selectedContentId, setSelectedContentId] = useState(null)
  
  const isAdmin = userRole === 'ADMIN'
  const isCreator = userRole === 'CREATOR' || isAdmin

  const [formData, setFormData] = useState({
    title: '',
    contentType: 'MUSIC',
    description: '',
    publishedDate: '',
    createdById: ''
  })

  const [rightsFormData, setRightsFormData] = useState({
    digitalContentId: '',
    rightsOwnerId: '',
    ownershipPercentage: '',
    rightsStartDate: new Date().toISOString().split('T')[0],
    rightsEndDate: '',
    rightsStatus: 'ACTIVE'
  })

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      setLoading(true)
      const [contentRes, usersRes] = await Promise.all([
        contentService.getAllContent().catch(() => ({ data: [] })),
        userService.getAllUsers().catch(() => ({ data: [] }))
      ])
      setContent(contentRes.data)
      setUsers(usersRes.data)
      setError(null)
    } catch (err) {
      setError('Failed to load data')
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
      await contentService.createDraft(formData)
      setFormData({ title: '', contentType: 'MUSIC', description: '', publishedDate: '', createdById: '' })
      setShowModal(false)
      loadData()
    } catch (err) {
      setError('Failed to create draft content')
    }
  }

  const handleRegister = async (id) => {
    try {
      await contentService.registerContent(id)
      loadData()
    } catch (err) {
      setError('Failed to register content')
    }
  }

  const handleApprove = async (id) => {
    try {
      await contentService.approveContent(id)
      loadData()
    } catch (err) {
      setError('Failed to approve content')
    }
  }

  const handleOpenRights = (contentId) => {
    setSelectedContentId(contentId)
    setRightsFormData(prev => ({ ...prev, digitalContentId: contentId }))
    setShowRightsModal(true)
  }

  const handleRightsSubmit = async (e) => {
    e.preventDefault()
    try {
      await contentRightsService.assignRights(rightsFormData)
      setShowRightsModal(false)
      setRightsFormData({
        digitalContentId: '',
        rightsOwnerId: '',
        ownershipPercentage: '',
        rightsStartDate: new Date().toISOString().split('T')[0],
        rightsEndDate: '',
        rightsStatus: 'ACTIVE'
      })
      alert('Rights assigned successfully!')
    } catch (err) {
      setError('Failed to assign rights')
    }
  }

  const getCreatorName = (id) => {
    const user = users.find(u => u.id === id)
    return user ? user.name : 'Unknown'
  }

  if (loading) return <div className="container"><div className="loading">Loading content...</div></div>

  return (
    <div className="container">
      <div className="page-header">
        <h1>Content Management</h1>
        {isCreator && <button onClick={() => setShowModal(true)}>+ Create Draft</button>}
      </div>

      {error && <div className="error">{error}</div>}

      <div className="card">
        <div className="table-container">
          <table>
            <thead>
              <tr>
                <th>Title</th>
                <th>Type</th>
                <th>Creator</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {content.map(item => (
                <tr key={item.id}>
                  <td>{item.title}</td>
                  <td>{item.contentType}</td>
                  <td>{getCreatorName(item.createdById)}</td>
                  <td><span className={`badge badge-${item.contentStatus?.toLowerCase()}`}>{item.contentStatus}</span></td>
                  <td>
                    <div className="action-buttons">
                      {item.contentStatus === 'DRAFT' && isCreator && (
                        <button className="btn-edit" onClick={() => handleRegister(item.id)}>Register</button>
                      )}
                      {item.contentStatus === 'REGISTERED' && isAdmin && (
                        <button className="btn-approve" onClick={() => handleApprove(item.id)}>Approve</button>
                      )}
                      {isAdmin && (
                        <button className="btn-rights" onClick={() => handleOpenRights(item.id)}>Manage Rights</button>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {content.length === 0 && <p className="no-data">No content found</p>}
      </div>

      {showModal && (
        <div className="modal active">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Add New Draft</h2>
              <button className="close-btn" onClick={() => setShowModal(false)}>×</button>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Title</label>
                <input type="text" name="title" value={formData.title} onChange={handleInputChange} required />
              </div>
              <div className="form-group">
                <label>Content Type</label>
                <select name="contentType" value={formData.contentType} onChange={handleInputChange}>
                  <option value="MUSIC">Music</option>
                  <option value="VIDEO">Video</option>
                  <option value="ARTICLE">Article</option>
                </select>
              </div>
              <div className="form-group">
                <label>Creator</label>
                <select name="createdById" value={formData.createdById} onChange={handleInputChange} required>
                  <option value="">Select Creator</option>
                  {users.filter(u => u.role === 'CREATOR').map(u => (
                    <option key={u.id} value={u.id}>{u.name}</option>
                  ))}
                </select>
              </div>
              <button type="submit" className="btn-submit">Create Draft</button>
            </form>
          </div>
        </div>
      )}

      {showRightsModal && (
        <div className="modal active">
          <div className="modal-content">
            <div className="modal-header">
              <h2>Assign Ownership Rights</h2>
              <button className="close-btn" onClick={() => setShowRightsModal(false)}>×</button>
            </div>
            <form onSubmit={handleRightsSubmit}>
              <div className="form-group">
                <label>Rights Owner</label>
                <select name="rightsOwnerId" value={rightsFormData.rightsOwnerId} onChange={(e) => setRightsFormData({...rightsFormData, rightsOwnerId: e.target.value})} required>
                  <option value="">Select Owner</option>
                  {users.filter(u => u.role === 'CREATOR' || u.role === 'DISTRIBUTOR').map(u => (
                    <option key={u.id} value={u.id}>{u.name}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Ownership Percentage (%)</label>
                <input type="number" step="0.01" name="ownershipPercentage" value={rightsFormData.ownershipPercentage} onChange={(e) => setRightsFormData({...rightsFormData, ownershipPercentage: e.target.value})} required />
              </div>
              <div className="form-group">
                <label>Start Date</label>
                <input type="date" name="rightsStartDate" value={rightsFormData.rightsStartDate} onChange={(e) => setRightsFormData({...rightsFormData, rightsStartDate: e.target.value})} required />
              </div>
              <button type="submit" className="btn-submit">Save Rights</button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default ContentManagement
