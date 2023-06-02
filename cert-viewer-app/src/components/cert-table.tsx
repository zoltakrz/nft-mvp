import { FormValues } from "./email-form";
import { Box, Table, Typography } from "@mui/material";
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
  type CertificateDTO = {
    name: string;
    surname: string;
    certType: string;
    certGrade: string;
    certLogoURL: string;
  };

  function getCertificates(email: string) {
    const certificates: Array<CertificateDTO> = [];

    let certificate1: CertificateDTO = {
        name: email,
        surname: "surname",
        certType: "type",
        certGrade: "grade",
        certLogoURL: "https://cdn.iconscout.com/icon/premium/png-256-thumb/certificate-4449259-3687567.png?f=webp&w=128"
      };

    certificates.push(certificate1)
    certificates.push(certificate1)

    return certificates
  }
  
  export const CertTable = ({email}: FormValues) => {
    let certificates = getCertificates(email)
    return  <>
              <Box sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
            }}>
                <Typography component="h1" variant="h5">
                    {email}
                </Typography>
              <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell align="right">Name</TableCell>
                    <TableCell align="right">Surname</TableCell>
                    <TableCell align="right">Certificate Type</TableCell>
                    <TableCell align="right">Certificate Grade</TableCell>
                    <TableCell align="right">Logo</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {certificates.map((row) => (
                    <TableRow
                      key={row.name}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                      <TableCell component="th" scope="row">
                        {row.name}
                      </TableCell>
                      <TableCell align="right">{row.name}</TableCell>
                      <TableCell align="right">{row.surname}</TableCell>
                      <TableCell align="right">{row.certType}</TableCell>
                      <TableCell align="right">{row.certGrade}</TableCell>
                      <TableCell align="right"><img src={row.certLogoURL}></img></TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
              </TableContainer>
              </Box>
            </>;
  };
