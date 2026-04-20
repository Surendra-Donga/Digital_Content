import { Link } from 'react-router-dom'
import './Navigation.css'

function Navigation() {
  return (
    <nav className="navbar">
      <div className="nav-container">
        <Link to="/" className="nav-brand">
          📚 Digital Content Rights
        </Link>
        <ul className="nav-menu">
          <li><Link to="/">Dashboard</Link></li>
          <li><Link to="/users">Users</Link></li>
          <li><Link to="/content">Content</Link></li>
          <li><Link to="/royalty">Royalty</Link></li>
          <li><Link to="/usage">Usage</Link></li>
        </ul>
      </div>
    </nav>
  )
}

export default Navigation
