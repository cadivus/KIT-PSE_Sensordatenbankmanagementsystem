import React, {createContext, FC, useEffect, useState} from 'react'
import {ReplayStoreContext} from "./ReplayStoreProvider";

export const SensorStoreContext = createContext('SensorStore')

export const SensorStoreProvider: FC = ({children}) => {
    return <SensorStoreContext.Provider value={"SensorStore"}>{children}</SensorStoreContext.Provider>
}
