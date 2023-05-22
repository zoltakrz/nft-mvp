import { useForm } from "react-hook-form";
import { TextField, Button, Stack } from "@mui/material";

type FormValues = {
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
            <h1>Enter Capgemini Mail</h1>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
            <Stack spacing={2} width={400}>
                <TextField
                    type="email"
                    {...submitEmail("email", {
                        required: "Email is required",
                        pattern: { value: /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@capgemini.com$/, message: "Invalid Capgemini email", }
                    }
                    )}
                    error={!!errors.email}
                    helperText={errors.email?.message} />
                <Button type="submit" variant="contained" color="primary">
                    GET CERTIFICATES
                </Button>
            </Stack>
        </form>
        </>;
    }

    function renderCertificateView() {
        return <>
            <h1>Certificates for: {email}</h1>
            <p>Certificate 1</p>
            <p>Certificate 2</p>
            <p>Certificate 3</p>
            <form onSubmit={handleSubmit(returnToForm)} noValidate>
                    <Button type="submit" variant="contained" color="primary">
                        RETURN
                    </Button>
            </form>
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
