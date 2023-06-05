import { Container, CssBaseline } from '@mui/material';
import './../styles.css';
import { EmailForm } from "./email-form";


function App() {
  return (
    <Container component="main">
      <CssBaseline />
      {  <EmailForm /> }
    </Container>
  );
}

export default App;
