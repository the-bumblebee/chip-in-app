import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import ChipInApp from './ChipInApp.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ChipInApp />
  </StrictMode>,
)
