import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Navigation from './components/Navigation'
import Dashboard from './pages/Dashboard'
import UserManagement from './pages/UserManagement'
import ContentManagement from './pages/ContentManagement'
import RoyaltyCalculation from './pages/RoyaltyCalculation'
import UsageTracking from './pages/UsageTracking'
import './App.css'

function App() {
  return (
    <Router>
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
  )
}

export default App
