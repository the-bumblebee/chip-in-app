import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import ChipInApp from './ChipInApp.tsx'
import { Provider } from '@/components/ui/provider.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider>
      <ChipInApp />
    </Provider>
  </StrictMode>,
)
