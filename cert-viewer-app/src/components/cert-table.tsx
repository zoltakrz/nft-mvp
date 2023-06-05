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
        name: "name",
        surname: "surname",
        certType: "type1",
        certGrade: "grade1",
        certLogoURL: "https://cdn.iconscout.com/icon/premium/png-256-thumb/certificate-4449259-3687567.png?f=webp&w=128"
      };

      let certificate2: CertificateDTO = {
        name: "name",
        surname: "surname",
        certType: "type2",
        certGrade: "grade2",
        certLogoURL: "https://cdn.iconscout.com/icon/premium/png-256-thumb/certificate-4449259-3687567.png?f=webp&w=128"
      };

    certificates.push(certificate1)
    certificates.push(certificate2)

    return certificates
  }
  
  export const CertTable = ({email}: FormValues) => {
    let certificates = getCertificates(email)
    return  <>
              <Box sx={{
                marginTop: 2,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center'}}>
              <Typography component="h1" variant="h5" paddingBottom={3}>
                  {email}
              </Typography>
              <TableContainer component={Paper} variant="outlined">
                <Table sx={{ minWidth: 650, borderRadius: '0', elevation: 0, }} aria-label="simple table">
                  <TableHead>
                    <TableRow>
                      <TableCell align="left">Name</TableCell>
                      <TableCell align="left">Surname</TableCell>
                      <TableCell align="left">Certificate Type</TableCell>
                      <TableCell align="left">Certificate Grade</TableCell>
                      <TableCell align="left">Logo</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {certificates.map((row) => (
                      <TableRow
                        key={row.certType + row.certGrade}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                      >
                        <TableCell align="left">{row.name}</TableCell>
                        <TableCell align="left">{row.surname}</TableCell>
                        <TableCell align="left">{row.certType}</TableCell>
                        <TableCell align="left">{row.certGrade}</TableCell>
                        <TableCell align="left"><img src={row.certLogoURL}></img></TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
              </Box>
            </>;
  };
