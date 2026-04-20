export const formatDate = (date) => {
  if (!date) return 'N/A'
  return new Date(date).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

export const formatCurrency = (amount) => {
  if (!amount) return '$0.00'
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD'
  }).format(amount)
}

export const formatPercentage = (value) => {
  if (!value) return '0%'
  return `${(value * 100).toFixed(2)}%`
}

export const getStatusBadgeClass = (status) => {
  const statusLower = status?.toLowerCase() || ''
  if (statusLower.includes('active') || statusLower.includes('success') || statusLower.includes('verified')) {
    return 'badge-success'
  } else if (statusLower.includes('pending') || statusLower.includes('initiated')) {
    return 'badge-warning'
  } else if (statusLower.includes('failed') || statusLower.includes('inactive')) {
    return 'badge-danger'
  }
  return 'badge-info'
}
