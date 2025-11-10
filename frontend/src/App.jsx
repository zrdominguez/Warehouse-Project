import React, { useEffect, useState } from 'react'

export default function App() {

    const [message, setMessage] = useState('');
    
    useEffect(() => {
        fetch('http://localhost:8080/api/hello')
        .then(res => res.text())
        .then(data => setMessage(data))
    }, [])


  return (
    <div>
        <h1 className=''>Fullstack App</h1>
        <p>{message}</p>
    </div>
  )
}
