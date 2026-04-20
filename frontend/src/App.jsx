import { useState, useEffect, createContext, useContext } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Navigation from './components/Navigation'
import Dashboard from './pages/Dashboard'
import UserManagement from './pages/UserManagement'
import ContentManagement from './pages/ContentManagement'
import RoyaltyCalculation from './pages/RoyaltyCalculation'
import UsageTracking from './pages/UsageTracking'
import './App.css'

export const AuthContext = createContext()

function App() {
  const [userRole, setUserRole] = useState('ADMIN') // Simulated role

  return (
    <AuthContext.Provider value={{ userRole, setUserRole }}>
      <Router>
        <div className="role-switcher">
          <span>Simulate Role: </span>
          <select value={userRole} onChange={(e) => setUserRole(e.target.value)}>
            <option value="ADMIN">Admin</option>
            <option value="CREATOR">Creator</option>
            <option value="DISTRIBUTOR">Distributor</option>
          </select>
        </div>
        <Navigation />
        <main>
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/users" element={<UserManagement />} />
            <Route path="/content" element={<ContentManagement />} />
            <Route path="/royalty" element={<RoyaltyCalculation />} />
            <Route path="/usage" element={<UsageTracking />} />
          </Routes>
        </main>
      </Router>
    </AuthContext.Provider>
  )
}

export default App
