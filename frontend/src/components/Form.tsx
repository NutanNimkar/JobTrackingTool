import React, { useState } from "react";
import { TextField, Button, Grid, Typography, Alert, Box } from "@mui/material";
import axios from "axios";

interface FormData {
    roleName: string;
    companyName: string;
    status: string;
    date: string;
}

const Form: React.FC = () => {
    const [formData, setFormData] = useState<FormData>({
        roleName: "",
        companyName: "",
        status: "",
        date: "",
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccess(false);
        console.log(formData);
        try {
            const response = await axios.post(
                "/api/sheets/add",
                formData,
            );
            console.log("Success:", response.data);
            setSuccess(true);
        } catch (err: any) {
            console.error("Error:", err.response?.data || err.message);
            setError(err.response?.data || "An error occurred while submitting the form.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ maxWidth: 600, margin: "auto", padding: 4 }}>
            <Typography variant="h4" gutterBottom>
                Job Application Tracker
            </Typography>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Role Name"
                            name="roleName"
                            value={formData.roleName}
                            onChange={handleInputChange}
                            placeholder="Enter role name"
                            variant="outlined"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Company Name"
                            name="companyName"
                            value={formData.companyName}
                            onChange={handleInputChange}
                            placeholder="Enter company name"
                            variant="outlined"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Status"
                            name="status"
                            value={formData.status}
                            onChange={handleInputChange}
                            placeholder="Enter status (e.g., Waiting, Offer)"
                            variant="outlined"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Date"
                            name="date"
                            type="date"
                            value={formData.date}
                            onChange={handleInputChange}
                            InputLabelProps={{
                                shrink: true,
                            }}
                            variant="outlined"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button
                            fullWidth
                            type="submit"
                            variant="contained"
                            color="primary"
                            disabled={loading}
                        >
                            {loading ? "Submitting..." : "Submit"}
                        </Button>
                    </Grid>
                </Grid>
            </form>

            {error && (
                <Box mt={2}>
                    <Alert severity="error">{error}</Alert>
                </Box>
            )}
            {success && (
                <Box mt={2}>
                    <Alert severity="success">Form submitted successfully!</Alert>
                </Box>
            )}
        </Box>
    );
};

export default Form;
