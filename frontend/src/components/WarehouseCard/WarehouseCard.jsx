import { Card, CardContent, Typography, CardActions, Button } from "@mui/material";
import "./WarehouseCard.css";
import { useNavigate } from "react-router-dom";
import ConfirmDeleteToast from "../../custom-toasts/ConfirmDeleteToast/ConfirmDeleteToast";
import toast, { Toaster } from 'react-hot-toast';
import { useState } from "react";
import EditWarehouseModal from "../../modals/EditWarehouseModal";

export default function WarehouseCard({warehouse, loadWarehouses, setError}) {
  const navigate = useNavigate();
  const [openModal, setOpenModal] = useState(false);

  const handleView =  () => {
    navigate(`/warehouse/${warehouse.id}`)
  }

  const onConfirm = async warehouseId => {
    try{
      const response = await fetch(
        `http://localhost:8080/warehouse/${warehouseId}`,
        {
          method: 'DELETE',
          headers:{
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({userId: 1})
        }
      );
      if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
      loadWarehouses();
    }catch(error){
      setError(error);
    }
  }

  const handleDelete = onConfirm => {
    toast.custom(t => (
      <ConfirmDeleteToast t={t} onConfirm={onConfirm} />
    ))
  }

  const handleOnSubmit = async (warehouse, warhouseId) => {
    try{
      const response = await fetch(
        `http://localhost:8080/warehouse/${warhouseId}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(warehouse)
        }
      );
      if(!response.ok) throw new Error(`There was an error! status: ${response.status}`)
      loadWarehouses();
    }catch(error){
      setError(error);
    }finally{
      setOpenModal(false);
    }
  } 


  return (
    <Card sx={{ minWidth: 250, marginRight: 2, position: "relative"}}>
      <EditWarehouseModal 
        open={openModal}
        onClose={() => setOpenModal(false)}
        onSubmit={handleOnSubmit}
        warehouse={warehouse}
      />
      <div><Toaster /></div>
      <CardContent>
        <Button
        size="small"
        onClick={() => handleDelete( () => onConfirm(warehouse.id))}
        sx={{
          position: "absolute",
          top: 8,
          right: 8,
          minWidth: "30px",
          padding: "2px 6px",
        }}
        >X</Button>
        <Typography variant="h6" gutterBottom>
          {warehouse.name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Location: {warehouse.location}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Capacity: {warehouse.capacity}
        </Typography>
      </CardContent>
      <CardActions>
        <Button 
        size="small"
        onClick={handleView}
        >View</Button>
        <Button 
        size="small"
        onClick={()=> setOpenModal(true)}
        >Edit</Button>
      </CardActions>
    </Card>
  );
}
