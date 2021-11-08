import React, { useState, useEffect, forwardRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Grid, TextField } from '@material-ui/core';
import Alert from '@material-ui/lab/Alert';
import axios from 'axios';
import Table from '../Table';

function GatewayDetails() {
  const columns = [
    { title: 'Id', field: 'id', editable: false },
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
    { title: 'Date created', field: 'createdDate', editable: false },
  ];
  const [formData, setFormData] = useState({});
  const [devices, setDevices] = useState([]);
  const [apiErrors, setApiErrors] = useState([]);
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    axios
      .get(`/api/gateways/${id}`)
      .then((res) => {
        const { serialNumber, name, ipv4Address, devices } = res.data;
        setFormData({ serialNumber, name, ipv4Address });
        setDevices(devices);
      })
      .catch(() => {});
  }, []);

  const handleUpdate = async (evt) => {
    evt.preventDefault();
    const updateData = { ...formData, devices };
    try {
      const reponse = await axios.patch(`/api/gateways/${id}`, updateData);
      if (reponse.data) {
        const { serialNumber, name, ipv4Address, devices } = reponse.data;
        setFormData({ serialNumber, name, ipv4Address });
        setDevices(devices);
      }
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
        <h1>Gateway Details</h1>
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
        <form onSubmit={handleUpdate}>
          <Grid container direction="row" justifyContent="space-between" alignItems="center">
            <TextField
              label="Serial Number"
              id="margin-normal"
              name="serialNumber"
              helperText="Enter serial number"
              disabled
              value={formData.serialNumber}
              onChange={handleChange}
            />
            <TextField
              label="Name"
              id="margin-normal"
              name="name"
              helperText="Gateway name"
              value={formData.name}
              onChange={handleChange}
            />
            <TextField
              label="Ip Address"
              id="margin-normal"
              name="ipv4Address"
              helperText="IP address of gateway"
              value={formData.ipv4Address}
              onChange={handleChange}
            />
          </Grid>
          <br />
          <Table
            title="Devices"
            columns={columns}
            data={devices}
            editable={{
              onRowAdd: (newData) =>
                new Promise((resolve, reject) => {
                  setTimeout(() => {
                    setDevices([...devices, newData]);

                    resolve();
                  }, 200);
                }),
              onRowUpdate: (newData, oldData) =>
                new Promise((resolve, reject) => {
                  setTimeout(() => {
                    const dataUpdate = [...devices];
                    const index = oldData.tableData.id;
                    dataUpdate[index] = newData;
                    setDevices([...dataUpdate]);

                    resolve();
                  }, 200);
                }),
              onRowDelete: (oldData) =>
                new Promise((resolve, reject) => {
                  setTimeout(() => {
                    const dataDelete = [...devices];
                    const index = oldData.tableData.id;
                    dataDelete.splice(index, 1);
                    setDevices([...dataDelete]);

                    resolve();
                  }, 200);
                }),
            }}
          />
          <br />
          <div>
            <Button
              variant="contained"
              color="primary"
              onClick={handleUpdate}
              style={{ marginRight: '32px' }}
            >
              Save
            </Button>
            <Button variant="contained" color="secondary" onClick={handleCancel}>
              Back To Gateways
            </Button>
          </div>
        </form>
      </Grid>

      <Grid item xs={2} />
    </Grid>
  );
}
export default GatewayDetails;
