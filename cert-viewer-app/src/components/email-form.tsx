import { useForm } from "react-hook-form";
import { TextField, Button, Box, Typography } from "@mui/material";
import { CertTable } from "./cert-table";

export type FormValues = {
  email: string;
};

let email = "";

export const EmailForm = () => {
    const {
        handleSubmit,
        register: submitEmail,
        formState: { errors },
    } = useForm<FormValues>({
        defaultValues: {
        email: "",
        },
    });

    const onSubmit = (data: FormValues) => {
        console.log("Fetching certificates for: " + data.email);
        email = data.email;
    };

    const returnToForm = () => {
        email = "";
    };

    function renderEmailForm() {
        return <>
                <Box sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}>
                    <Typography component="h1" variant="h5">
                        Enter Capgemini Mail
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate sx={{ mt: 1 }} textAlign='center'>
                        <TextField
                            type="email"
                            margin="normal"
                            fullWidth
                            id="email"
                            label="Email Address"
                            autoComplete="email"
                            autoFocus
                            {...submitEmail("email", {
                                required: "Email is required",
                                pattern: { value: /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@capgemini.com$/, message: "Invalid Capgemini email", }
                            }
                            )}
                            error={!!errors.email}
                            helperText={errors.email?.message} />
                        <Button 
                            type="submit" 
                            variant="contained" 
                            color="primary"
                            centerRipple
                            sx={{ mt: 3, mb: 2 }}>
                                GET CERTIFICATES
                        </Button>
                    </Box>
                </Box>
        </>;
    }

    function renderCertificateView() {
        return <>

                <CertTable {...{email}}/>
                <Box component="form" onSubmit={handleSubmit(returnToForm)} noValidate sx={{ mt: 1 }} textAlign='center'>
                    <Button 
                        type="submit" 
                        variant="contained" 
                        color="primary"
                        centerRipple
                        sx={{ mt: 3, mb: 2 }}>
                            RETURN
                    </Button>
                </Box>
        </>;
    }

    // BODY
    if (email === "") {
        return renderEmailForm();
    } else {
        return renderCertificateView();
    }
    // END OF BODY
};
