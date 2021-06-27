import React from 'react'
import useSensorStore from '../../hooks/UseSensorStore'


/**
 *  Displays a list of sensors.
 *  This class implements a React component.
 */
const SensorList = () => {
    const sensorStore = useSensorStore()

    return (
         <ul>
             {sensorStore?.sensors.map((reptile) => (
                 <li>{reptile.name.name}</li>
             ))}
         </ul>
     )
}

export default SensorList