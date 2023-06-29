import { FormValues } from "./email-form";
import { Box, Table, Typography } from "@mui/material";
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useEffect, useState } from "react";
import { getCertificates } from "./blockchain-fetcher";

export type CertificateDTO = {
  name: string;
  surname: string;
  certType: string;
  certGrade: string;
  mintURL: string;
  certLogoURL: string;
};

export const CertTable = ({ email }: FormValues) => {
  const certificateContracts: CertificateDTO[] = []
  const [catUrl, certificates] = useState(certificateContracts);
  const [error, setError] = useState(false);
  const [state, setState] = useState('');

  useEffect(() => {
    setState('loading');
    getCertificates(email)
      .then((res) => {
        setState('success');
        certificates(res);
      })
      .catch((err) => {
        console.error('Error:', err);
        setState('error');
        setError(err);
      });
  }, []);

  if (state === 'error')
    return (
      <h1>
        {error.toString()}
      </h1>
    );
  return (
    <>
      {state === 'loading' ? (
        <div>Loading...</div>
      ) : (
        <Box sx={{
          marginTop: 2,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }}>
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
                  <TableCell align="left">Minted NFT</TableCell>
                  <TableCell align="left">Logo</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {catUrl.map((row) => (
                  <TableRow
                    key={row.certType + row.certGrade}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell align="left">{row.name}</TableCell>
                    <TableCell align="left">{row.surname}</TableCell>
                    <TableCell align="left">{row.certType}</TableCell>
                    <TableCell align="left">{row.certGrade}</TableCell>
                    <TableCell align="left"><a href={row.mintURL}>Link</a></TableCell>
                    <TableCell align="left"><img src={row.certLogoURL} width={300} height={'auto'}></img></TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Box>
      )}
    </>
  );
};
