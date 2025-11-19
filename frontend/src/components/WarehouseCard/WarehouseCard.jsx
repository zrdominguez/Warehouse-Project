import { Card, CardContent, Typography, CardActions, Button } from "@mui/material";
import "./WarehouseCard.css";

export default function WarehouseCard({warehouse}) {
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
        <Button size="small">View</Button>
        <Button size="small">Edit</Button>
      </CardActions>
    </Card>
  );
}
