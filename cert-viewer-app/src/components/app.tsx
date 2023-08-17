import { Container, CssBaseline } from '@mui/material';
import './../styles.css';
import { EmailForm } from "./email-form";
import ReactGA from 'react-ga';
import React, {useEffect } from 'react';
import TagManager from 'react-gtm-module'

const TRACKING_ID = "G-RT6QSME5CP"; // OUR_TRACKING_ID
ReactGA.initialize(TRACKING_ID);

const tagManagerArgs = {
    gtmId: '<G-RT6QSME5CP>'
}
TagManager.initialize(tagManagerArgs)

function App() {

    useEffect(() => {
        ReactGA.pageview(window.location.pathname + window.location.search);
    }, []);

  return (
    <Container component="main">
      <CssBaseline />
      {  <EmailForm /> }
    </Container>
  );
}

export default App;
