import React, { useState } from 'react';
import { Button, TextField, Grid } from '@material-ui/core';
import Alert from '@material-ui/lab/Alert';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Table from '../Table';

function CreateGateway() {
  const columns = [
    {
      title: 'Vendor',
      field: 'vendor',
      validate: (rowData) => (rowData.vendor === '' ? 'Vendor cannot be empty' : ''),
    },
    {
      title: 'Status',
      field: 'status',
      lookup: { ONLINE: 'ONLINE', OFFLINE: 'OFFLINE' },
      validate: (rowData) => (rowData.status ? '' : 'Status cannot be empty'),
    },
  ];
  const [formData, setFormData] = useState({});
  const [devices, setDevices] = useState([]);

  const [apiErrors, setApiErrors] = useState([]);
  const navigate = useNavigate();

  const handleSubmit = async (evt) => {
    evt.preventDefault();
    if (!formData.serialNumber || !formData.name) {
      return;
    }
    const submitData = { ...formData, devices };
    try {
      await axios.post('/api/gateways', submitData);
      navigate('/gateways');
    } catch (error) {
      if (error.response?.data?.errors) {
        setApiErrors(error.response.data.errors);
      }
    }
  };

  const handleCancel = () => {
    navigate('/gateways');
  };

  const handleChange = (evt) => {
    const { name } = evt.target;
    const newValue = evt.target.value;
    setFormData({ ...formData, [name]: newValue });
  };

  return (
    <Grid container spacing={1}>
      <Grid item xs={2} />
      <Grid item xs={8}>
        <h1>Add Gateway</h1>
        {apiErrors.length > 0 ? (
          <>
            <Alert severity="error">
              {apiErrors.map((err) => (
                <li>{err.message}</li>
              ))}
            </Alert>
            <br />
          </>
        ) : null}
        <form onSubmit={handleSubmit}>
          <Grid container direction="row" justifyContent="space-between" alignItems="center">
            <TextField
              label="Serial Number"
              id="margin-normal"
              name="serialNumber"
              helperText="Enter serial number"
              required
              error={!formData.serialNumber}
              onChange={handleChange}
            />
            <TextField
              label="Name"
              id="margin-normal"
              name="name"
              helperText="Gateway name"
              required
              error={!formData.name}
              onChange={handleChange}
            />
            <TextField
              label="Ip Address"
              id="margin-normal"
              name="ipv4Address"
              helperText="IP address of gateway"
              onChange={handleChange}
            />
          </Grid>
          <br />
          <Table
            title="Devices"
            columns={columns}
            data={devices}
            options={{
              pageSize: 10,
              pageSizeOptions: [10, 20],
            }}
            editable={{
              onRowAdd: (newData) =>
                new Promise((resolve, reject) => {
                  setTimeout(() => {
                    setDevices([...devices, newData]);

                    resolve();
                  }, 100);
                }),
              onRowUpdate: (newData, oldData) =>
                new Promise((resolve, reject) => {
                  setTimeout(() => {
                    const dataUpdate = [...devices];
                    const index = oldData.tableData.id;
                    dataUpdate[index] = newData;
                    setDevices([...dataUpdate]);

                    resolve();
                  }, 100);
                }),
              onRowDelete: (oldData) =>
                new Promise((resolve, reject) => {
                  setTimeout(() => {
                    const dataDelete = [...devices];
                    const index = oldData.tableData.id;
                    dataDelete.splice(index, 1);
                    setDevices([...dataDelete]);

                    resolve();
                  }, 100);
                }),
            }}
          />
          <br />
          <div>
            <Button
              variant="contained"
              color="primary"
              onClick={handleSubmit}
              style={{ marginRight: '32px' }}
            >
              Add Gateway
            </Button>
            <Button variant="contained" color="secondary" onClick={handleCancel}>
              Cancel
            </Button>
          </div>
        </form>
      </Grid>

      <Grid item xs={2} />
    </Grid>
  );
}
export default CreateGateway;
