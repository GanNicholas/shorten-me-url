import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useLocation, Link } from "react-router-dom";
import { Form, Row, Col, Card, Button } from "react-bootstrap";
import { FormControl, Input, TextField } from "@mui/material";
import axios from "axios";
import { toast } from "react-toastify";
import Logo from "../images/hello.png";
import "./shorten-url.css";

import { Check } from "react-bootstrap-icons";

const ShortenUrlPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [shortenedUrl, setShortenedUrl] = useState(null);
  const [screenWidth, setScreenWidth] = useState(window.innerWidth);

  useEffect(() => {
    // console.log("Updating screen width: " + window.innerWidth);
    setScreenWidth(window.innerWidth);
  }, [window.innerWidth]);

  const shortenHandler = async (event) => {
    event.preventDefault();
    setIsSubmitting(true);
    const userInputUrl = document.getElementById("fullUrlAddress").value;

    if (
      document.getElementById("fullUrlAddress").value === null ||
      document.getElementById("fullUrlAddress").value === undefined ||
      document.getElementById("fullUrlAddress").value == ""
    ) {
      toast.error("Please enter a URL before submitting!");
      setIsSubmitting(false);
      return;
    }

    if (isValidUrl(userInputUrl) === false) {
      toast.error("Please enter a valid URL!");
      setIsSubmitting(false);
      return;
    } 
    
    // else if (
    //   userInputUrl.indexOf("http://") === -1 &&
    //   userInputUrl.indexOf("https://") === -1
    // ) {
    //   toast.error(
    //     "Please enter a valid URL starting with 'http://' or 'https://'."
    //   );
    //   setIsSubmitting(false);
    //   return;
    // }

    //  else if (userInputUrl.indexOf("www") === -1) {
    //   toast.error("Please enter a valid URL containing 'www' !");
    //   setIsSubmitting(false);
    //   return;
    // } else if (userInputUrl.indexOf(".com") === -1) {
    //   toast.error("Please enter a valid URL ending with '.com' !");
    //   setIsSubmitting(false);
    //   return;
    // }

    const controller = new AbortController();
    const fullUrl = window.location.href;
    const shortenedUrlHeader = fullUrl.slice(0, fullUrl.lastIndexOf("/")) + "/";
    var urlObj = {};

    urlObj["fullUrl"] = document.getElementById("fullUrlAddress").value;
    urlObj["shortenUrl"] = shortenedUrlHeader;
    // console.log("Full URL: " + urlObj.fullUrl);
    // console.log("Check API: " + process.env.REACT_APP_API_EXPRESS);

    setIsSubmitting(false);
    axios
      .post(`${process.env.REACT_APP_API_EXPRESS}/api/url/create`, urlObj)
      .then((response) => {
        // console.log(response.data);

        setShortenedUrl(shortenedUrlHeader + response.data);
        setIsSubmitting(false);
        toast.success("Url has been successfully shortened!");
      })
      .catch(function (err) {
        toast.error(err.response.data);
        setIsSubmitting(false);
        setShortenedUrl(null);
      });

    return () => {
      setShortenedUrl(null);
      setIsSubmitting(false);
      controller.abort();
    };
  };

  const isValidUrl = (urlString) => {
    var urlPattern = new RegExp(
      "^(https?:\\/\\/)?" + // validate protocol
        "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|" + // validate domain name
        "((\\d{1,3}\\.){3}\\d{1,3}))" + // validate OR ip (v4) address
        "(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*" + // validate port and path
        "(\\?[;&a-z\\d%_.~+=-]*)?" + // validate query string
        "(\\#[-a-z\\d_]*)?$",
      "i"
    ); // validate fragment locator
    return !!urlPattern.test(urlString);
  };

  return (
    <div className="background-img">
      <div className="shorten-container">
        <Card className="shorten-card">
          <Card.Header className="shorten-card_header">
            <Card.Title>
              <img src={Logo} alt="Shorten My Url Logo" width="120" />
              <h4>Shorten My URL</h4>
            </Card.Title>
          </Card.Header>
          <Card.Body>
            {shortenedUrl &&
            shortenedUrl !== null &&
            shortenedUrl !== undefined ? (
              <div>
                <span>
                  {" "}
                  <b>Your shortened link is:</b>{" "}
                  <a href={shortenedUrl}>{shortenedUrl}</a>
                </span>
                {/* <p>{shortenedUrl}</p>
                <a href={shortenedUrl} >{shortenedUrl}</a> */}
              </div>
            ) : null}
            <br />
            <Form id="shortenUrlForm" className="form-margin-width">
              <Row className="mb-3">
                <Form.Group as={Col} controlId="fullUrlAddress">
                  <Form.Label>URL Address</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="https://www.example.com"
                    name="fullUrl"
                    required
                  />
                </Form.Group>
              </Row>
              <div className="shorten-card_bottom-container">
                {screenWidth && parseInt(screenWidth) > 333 ? (
                  <Button
                    type="submit"
                    onClick={shortenHandler}
                    disabled={isSubmitting}
                    className="btn btn-primary shorten-button mt-1"
                  >
                    {isSubmitting && (
                      <span className="spinner-border spinner-border-sm mr-1" />
                    )}

                    {!isSubmitting ? " Shorten This url" : " Loading"}
                  </Button>
                ) : (
                  <Button
                    type="submit"
                    onClick={shortenHandler}
                    disabled={isSubmitting}
                    className="btn btn-primary shorten-button mt-1"
                  >
                    {isSubmitting && (
                      <span className="spinner-border spinner-border-sm mr-1" />
                    )}

                    {!isSubmitting ? <Check /> : null}
                  </Button>
                )}
              </div>
            </Form>
          </Card.Body>
          <Card.Footer className="shorten-card_footer">
            <p>
              Use me to shorten a link by clicking <i>'Shorten This url'</i> !
            </p>
            <p>
              Use the new URL on your web browser and return to the link you
              provided!
            </p>
          </Card.Footer>
        </Card>
      </div>
    </div>
  );
};

export default ShortenUrlPage;
