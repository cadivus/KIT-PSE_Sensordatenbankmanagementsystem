import React from 'react'
import {mount} from 'enzyme'
import {waitFor} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../../test/mock/store/communication/restClientMock'
import {thing1, thing2, thing3} from '../../../test/mock/store/communication/mockData/backend/getJson'
import Providers from '../Providers'
import ThingList from './ThingList'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for things', async () => {
  const wrapper = mount(
    <Providers>
      <ThingList selectedThings={new Set()} searchExpression={new RegExp('^.*$', 'i')} />
    </Providers>,
  )

  // Loading detection
  await waitFor(() => {
    expect(wrapper.html().includes('data-testid="loadingSpinner"')).toBe(false)
  })

  expect(wrapper.html().includes(thing1.name)).toBe(true)
  expect(wrapper.html().includes(thing2.name)).toBe(true)
  expect(wrapper.html().includes(thing3.name)).toBe(true)
})
