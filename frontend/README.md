# Digital Content Rights - React Frontend

A modern React frontend for managing digital content rights, royalty payments, and usage tracking.

## Features

- **Dashboard**: Overview of system statistics and quick access to features
- **User Management**: Create, read, update, and delete users (Admin, Creator, Distributor roles)
- **Content Management**: Upload and manage digital content with metadata
- **Usage Tracking**: Record and monitor content usage by distributors
- **Royalty Management**: Calculate and process royalty payments to creators

## Tech Stack

- **React 18**: UI library
- **Vite**: Fast build tool and dev server
- **React Router**: Client-side routing
- **Axios**: HTTP client for API requests
- **CSS3**: Styling with modern CSS

## Installation

### Prerequisites
- Node.js 16+ and npm/yarn

### Setup Steps

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The application will open at `http://localhost:3000`

## API Configuration

The frontend is configured to proxy API requests to `http://localhost:8080/api`. 

Make sure your Spring Boot backend is running on port 8080 before starting the frontend.

### API Endpoints Used

- **Users**: `/api/users`
- **Content**: `/api/content`
- **Usage Transactions**: `/api/usage`
- **Royalty**: `/api/royalty`
- **Rights**: `/api/rights`

## Project Structure

```
frontend/
├── src/
│   ├── components/          # Reusable components
│   │   └── Navigation.jsx   # Navigation bar
│   ├── pages/              # Page components
│   │   ├── Dashboard.jsx
│   │   ├── UserManagement.jsx
│   │   ├── ContentManagement.jsx
│   │   ├── UsageTracking.jsx
│   │   └── RoyaltyCalculation.jsx
│   ├── services/           # API service layer
│   │   └── apiService.js   # Axios instance and API calls
│   ├── utils/              # Utility functions
│   │   └── helpers.js      # Formatting and helper functions
│   ├── App.jsx             # Main app component
│   ├── App.css             # Global styles
│   ├── index.css           # Base styles
│   └── main.jsx            # React entry point
├── index.html              # HTML template
├── vite.config.js          # Vite configuration
├── package.json            # Dependencies
└── README.md               # This file
```

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build

## Features in Detail

### Dashboard
- Displays key statistics (total users, content, transactions, royalties)
- Quick navigation cards to main features

### User Management
- List all users with their roles (Admin, Creator, Distributor)
- Add new users
- Edit existing users
- Delete users

### Content Management
- Create new content with metadata (title, type, description)
- Supported content types: Music, Video, Article, Course, Podcast
- Track content status (Draft, Registered, Active, Inactive, Archived)
- Manage content creators

### Usage Tracking
- Record content usage events
- Track usage type (Stream, Download, View, Subscription Access)
- Monitor transaction status (Recorded, Verified, Settled)
- View revenue generated per transaction

### Royalty Management
- **Calculations Tab**: View and approve royalty calculations
- **Payments Tab**: Track royalty payments to creators
- **Calculate New Tab**: Calculate royalties for specific content

## Styling

The application uses a clean, modern design with:
- Blue accent color (#007bff) for primary actions
- Green for success/add actions
- Red for delete/danger actions
- Yellow for edit actions
- Responsive grid layouts
- Card-based UI components
- Hover effects and transitions

## Error Handling

- API errors are caught and displayed to the user
- Form validation for required fields
- Loading states during data fetching
- Success and error messages

## Environment Variables

No environment variables required for basic setup. If you need to customize the API URL:

Edit `src/services/apiService.js`:
```javascript
const API_BASE_URL = 'http://your-api-url/api'
```

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Future Enhancements

- User authentication and login
- Advanced filtering and search
- Export data to CSV/PDF
- Charts and analytics
- Dark mode theme
- Mobile app with React Native
- Real-time notifications

## Contributing

1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

## License

MIT License - See LICENSE file for details
