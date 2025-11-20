import { Card, CardContent, Typography, CardActions, Button } from "@mui/material";
import "./WarehouseCard.css";
import { useNavigate } from "react-router-dom";

export default function WarehouseCard({warehouse}) {
    const navigate = useNavigate();

    const handleView =  () => {
        navigate(`/warehouse/${warehouse.id}`)
    }
  return (
    <Card sx={{ minWidth: 250, marginRight: 2 }}>
      <CardContent>
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
        <Button size="small">Edit</Button>
      </CardActions>
    </Card>
  );
}
