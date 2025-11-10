import React, { useEffect, useState } from 'react'
import "./App.css"

export default function App() {

    const [message, setMessage] = useState('');
    
    useEffect(() => {
        fetch('http://localhost:8080/api/hello')
        .then(res => res.text())
        .then(data => setMessage(data))
    }, [])


  return (
    <div className='flex flex-col justify-center items-center h-screen'>
        <h1 className='font-bold text-blue-600'>Fullstack App</h1>
        <p>{message}</p>
    </div>
  )
}
