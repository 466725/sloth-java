"use client";

import { FormEvent, useState } from "react";

type LoginResponse = {
  message: string;
};

export default function Home() {
  const [message, setMessage] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [hasError, setHasError] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setIsSubmitting(true);
    setMessage("");
    setHasError(false);

    const formData = new FormData(event.currentTarget);
    const body = new URLSearchParams();
    body.set("username", String(formData.get("username") ?? ""));
    body.set("password", String(formData.get("password") ?? ""));

    try {
      const response = await fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body,
      });

      if (!response.ok) {
        throw new Error(`Login request failed (${response.status}).`);
      }

      const payload: unknown = await response.json();
      if (
        typeof payload !== "object" ||
        payload === null ||
        !("message" in payload) ||
        typeof payload.message !== "string"
      ) {
        throw new Error("The login service returned an invalid response.");
      }

      setMessage(payload.message);
      setHasError(payload.message === "wrong credentials");
    } catch (error) {
      setHasError(true);
      setMessage(
        error instanceof Error
          ? error.message
          : "Unable to reach the login service. Please try again.",
      );
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <main className="page-shell">
      <div className="ambient ambient-left" aria-hidden="true" />
      <div className="ambient ambient-right" aria-hidden="true" />

      <section className="login-layout" aria-labelledby="login-title">
        <div className="intro">
          <div className="brand-mark" aria-hidden="true">
            <span />
            <span />
            <span />
          </div>
          <p className="eyebrow">AI STOCK</p>
          <h1 id="login-title">
            Welcome
            <br />
            <span>back.</span>
          </h1>
          <p className="intro-copy">
            Sign in to explore the ai-stock application.
          </p>
          <div className="status-note">
            <span className="status-dot" />
            <span>Secure sign-in</span>
          </div>
        </div>

        <div className="login-card">
          <div className="card-heading">
            <p className="card-kicker">YOUR ACCOUNT</p>
            <h2>Sign in</h2>
            <p>Enter your credentials to continue.</p>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="field">
              <label htmlFor="username">Username</label>
              <input
                autoComplete="username"
                id="username"
                name="username"
                placeholder="Enter your username"
                required
                type="text"
              />
            </div>

            <div className="field">
              <label htmlFor="password">Password</label>
              <input
                autoComplete="current-password"
                id="password"
                name="password"
                placeholder="Enter your password"
                required
                type="password"
              />
            </div>

            <button disabled={isSubmitting} type="submit">
              {isSubmitting ? "Signing in..." : "Sign in"}
              {!isSubmitting && <span aria-hidden="true">→</span>}
            </button>
          </form>

          <p
            aria-live="polite"
            className={`feedback${hasError ? " feedback-error" : ""}`}
            role={hasError ? "alert" : "status"}
          >
            {message}
          </p>
          <p className="demo-hint">
            Demo credentials: <strong>username</strong> /{" "}
            <strong>password</strong>
          </p>
        </div>
      </section>

      <footer>Built with Next.js and Spring Boot</footer>
    </main>
  );
}
