import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";

const RedirectUserPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { keyword } = useParams();
  useEffect(() => {
    if (keyword !== null && keyword !== undefined) {
      // console.log("Redirect-user-page: " + keyword);
      const controller = new AbortController();
      var urlObj = {};
      urlObj["keyword"] = keyword;
      // console.log("Full URL: " + urlObj.fullUrl);
      // console.log("Check API: " + process.env.REACT_APP_API_EXPRESS);

      axios
        .put(`${process.env.REACT_APP_API_EXPRESS}/api/url/get`, urlObj)
        .then((response) => {
          // console.log(response.data);
          // var win = window.open(response.data, "_blank");
          // win.focus();
          window.location.replace(response.data);
        })
        .catch(function (err) {
          navigate("/home", { replace: true });
          // console.log(err)
          toast.error(err.response.data);
        });

      return () => {
        controller.abort();
      };
    } else {
      navigate("/home", { replace: true });
    }
  }, []);

  return <div></div>;
};

export default RedirectUserPage;
