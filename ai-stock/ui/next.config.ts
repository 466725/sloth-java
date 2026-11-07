import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: "export",
  turbopack: {
    root: process.cwd(),
  },
  ...(process.env.NODE_ENV === "development" && {
    async rewrites() {
      return [
        {
          source: "/api/:path*",
          destination: `${process.env.SPRING_BOOT_API_URL ?? "http://localhost:8080"}/api/:path*`,
        },
      ];
    },
  }),
};

export default nextConfig;
