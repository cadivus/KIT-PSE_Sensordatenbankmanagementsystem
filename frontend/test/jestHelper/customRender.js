import React from 'react'
import {render} from '@testing-library/react'
import Providers from '../../src/components/Providers'

export const renderWithProviders = ui => {
  const wrapper = ({children}) => <Providers>{children}</Providers>
  return render(ui, {wrapper})
}
