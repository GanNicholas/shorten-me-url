import React, { useEffect, useState } from "react";
import { CircularProgress } from "@mui/material";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Container, Header, Content, Footer as FooterContainer } from "rsuite";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "./App.css";

import ShortenUrlPage from "./Component/shorten-url-page/shorten-url";
import RedirectUserPage from "./Component/redirect-user-page/redirect-user-page";

import "react-toastify/dist/ReactToastify.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "rsuite/styles/index.less";
import "rsuite/dist/rsuite.min.css";

function App() {

  // Add a request interceptor - appends authorization header always
  axios.interceptors.request.use(function (config) {
    config.headers.put["Content-Type"] = "application/json";
    return config;
  });

  let routes = (
    <Routes>
      <Route exact path="/" element={<RedirectUserPage />} />
      <Route exact path="/:keyword" element={<RedirectUserPage />} />
      <Route exact path="/home" element={<ShortenUrlPage />} />
    </Routes>
  );

  return (
    <Router>
      <ToastContainer
        autoClose={3000}
        hideProgressBar={true}
        newestOnTop={true}
        closeOnClick={true}
        rtl={false}
        pauseOnHover={true}
        pauseOnFocusLoss={false}
        draggable={false}
        progress={undefined}
        position={toast.POSITION.TOP_RIGHT}
        style={{ zIndex: "10000", marginTop: "3%" }}
      />
      <div>
        <Container>
          <Header>
            <div className="">
              {/* {userId && role && (
                  <TopNavigation updateNav={updateNav} setNav={setUpdateNav} />
                )} */}
            </div>
          </Header>
          <Container>
            {routes}

            <FooterContainer>{/* <Footer /> */}</FooterContainer>
          </Container>
        </Container>
      </div>
    </Router>
  );
}

export default App;
