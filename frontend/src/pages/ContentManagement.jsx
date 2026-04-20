import { useState, useEffect } from 'react'
import { contentService, userService } from '../services/apiService'
import { formatDate } from '../utils/helpers'
import './ContentManagement.css'

function ContentManagement() {
  const [content, setContent] = useState([])
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [showModal, setShowModal] = useState(false)
  const [formData, setFormData] = useState({
    title: '',
    contentType: 'MUSIC',
    description: '',
    publishedDate: '',
    createdById: ''
  })
  const [editingId, setEditingId] = useState(null)

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
      if (editingId) {
        await contentService.updateContent(editingId, formData)
      } else {
        await contentService.createContent(formData)
      }
      setFormData({ title: '', contentType: 'MUSIC', description: '', publishedDate: '', createdById: '' })
      setEditingId(null)
      setShowModal(false)
      loadData()
    } catch (err) {
      setError('Failed to save content')
      console.error(err)
    }
  }

  const handleEdit = (item) => {
    setFormData({
      title: item.title,
      contentType: item.contentType,
      description: item.description,
      publishedDate: item.publishedDate,
      createdById: item.createdBy?.id
    })
    setEditingId(item.id)
    setShowModal(true)
  }

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this content?')) {
      try {
        await contentService.deleteContent(id)
        loadData()
      } catch (err) {
        setError('Failed to delete content')
      }
    }
  }

  if (loading) return <div className="container"><div className="loading">Loading content...</div></div>

  return (
    <div className="container">
      <div className="page-header">
        <h1>Content Management</h1>
        <button onClick={() => { setFormData({ title: '', contentType: 'MUSIC', description: '', publishedDate: '', createdById: '' }); setEditingId(null); setShowModal(true) }}>+ Add Content</button>
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
                <th>Published Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {content.map(item => (
                <tr key={item.id}>
                  <td>{item.title}</td>
                  <td>{item.contentType}</td>
                  <td>{item.createdBy?.name || 'N/A'}</td>
                  <td><span className={`badge badge-${item.contentStatus?.toLowerCase()}`}>{item.contentStatus}</span></td>
                  <td>{formatDate(item.publishedDate)}</td>
                  <td>
                    <div className="action-buttons">
                      <button className="btn-edit" onClick={() => handleEdit(item)}>Edit</button>
                      <button className="btn-delete" onClick={() => handleDelete(item.id)}>Delete</button>
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
              <h2>{editingId ? 'Edit Content' : 'Add New Content'}</h2>
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
                  <option value="COURSE">Course</option>
                  <option value="PODCAST">Podcast</option>
                </select>
              </div>
              <div className="form-group">
                <label>Description</label>
                <textarea name="description" value={formData.description} onChange={handleInputChange} rows="4"></textarea>
              </div>
              <div className="form-group">
                <label>Published Date</label>
                <input type="date" name="publishedDate" value={formData.publishedDate} onChange={handleInputChange} />
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
              <button type="submit" className="btn-submit">Save Content</button>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default ContentManagement
