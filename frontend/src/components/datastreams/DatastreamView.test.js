import {getJson, getText, postJsonGetText} from "../../store/communication/restClient";
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock
} from "../../test/mock/store/communication/restClientMock";
import {renderWithProviders} from "../../test/jestHelper/customRender";
import DatastreamView from './DatastreamView'
import React from "react";

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<DatastreamView />)

  const datastreamListViewContainer = getByTestId(/datastreamListView-Container/)
  expect(datastreamListViewContainer).toBeInTheDocument()
})