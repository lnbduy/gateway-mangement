import React, { useState, useEffect, forwardRef } from 'react';
import { Button, Grid } from '@material-ui/core';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Table from '../Table';

function GatewayList() {
  const columns = [
    { title: 'Serial Number', field: 'serialNumber' },
    { title: 'Name', field: 'name' },
    { title: 'IP address', field: 'ipv4Address' },
    { title: 'Number of devices', render: (rowData) => rowData.devices.length },
  ];
  const [data, setData] = useState([]);
  const [selectedRow, setSelectedRow] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get('/api/gateways')
      .then((res) => {
        setData(res.data);
      })
      .catch(() => {});
  }, []);

  const handleDelete = async () => {
    try {
      await axios.delete(`/api/gateways/${selectedRow.serialNumber}`);
      const newData = data.filter((row) => row.serialNumber !== selectedRow.serialNumber);
      setData(newData);
      setSelectedRow(null);
    } catch (error) {
      console.log('Error');
    }
  };
  const handleViewDetails = () => {
    if (selectedRow && selectedRow.serialNumber) {
      navigate(`/gateways/${selectedRow.serialNumber}`);
    }
  };

  return (
    <div className="App">
      <Grid container spacing={1}>
        <Grid item xs={2} />
        <Grid item xs={8}>
          <h1>Gateway List</h1>
          <Table
            options={{
              rowStyle: (rowData) => {
                const selected = selectedRow && selectedRow.tableData.id === rowData.tableData.id;
                return {
                  backgroundColor: selected ? '#fcdac2' : '#FFF',
                };
              },
            }}
            title="Gateways"
            columns={columns}
            data={data}
            onRowClick={(evt, selectedRow) => setSelectedRow(selectedRow)}
          />
          <br />
          <div>
            <Button
              variant="contained"
              color="primary"
              href="/add-gateway"
              style={{ marginRight: '32px' }}
            >
              Add Gateway
            </Button>
            <Button
              variant="contained"
              color="secondary"
              disabled={selectedRow === null}
              style={{ marginRight: '32px' }}
              onClick={handleViewDetails}
            >
              View Details
            </Button>
            <Button
              variant="contained"
              color="secondary"
              disabled={selectedRow === null}
              onClick={handleDelete}
            >
              Delete
            </Button>
          </div>
        </Grid>
        <Grid item xs={2} />
      </Grid>
    </div>
  );
}
export default GatewayList;
