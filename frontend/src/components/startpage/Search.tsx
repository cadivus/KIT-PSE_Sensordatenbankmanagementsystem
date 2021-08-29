import React, {useState} from 'react'
import {makeStyles} from '@material-ui/core/styles'
import SearchBar from 'material-ui-search-bar'

const useStyles = makeStyles(theme => ({
  root: {
    padding: '2px 4px',
    display: 'flex',
    alignItems: 'center',
    marginBottom: '10px',
  },
  input: {
    marginLeft: theme.spacing(1),
    flex: 1,
  },
  iconButton: {
    padding: 10,
  },
}))

/**
 *  Displays the search of the things.
 *  This class implements a React component.
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const Search = ({setSearchExpression}: {setSearchExpression: any}) => {
  const classes = useStyles()
  const [searchString, setSearchString] = useState('')

  const updateSearch = (value: string) => {
    setSearchString(value)
    setSearchExpression(new RegExp(`^.*${value}.*$`, 'i'))
  }

  return <SearchBar value={searchString} onChange={updateSearch} onCancelSearch={() => updateSearch('')} />
}

export default Search
