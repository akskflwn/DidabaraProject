import "./App.css";
import Router from "./Router";
import { RecoilRoot } from "recoil";
import { ThemeProvider } from "styled-components";
import { Theme } from "./Theme";
import { QueryClientProvider, QueryClient } from "react-query";
import { Suspense } from "react";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <Suspense fallback={<div>Loading</div>}>
          <ThemeProvider theme={Theme}>
            <Router />
          </ThemeProvider>
        </Suspense>
      </RecoilRoot>
    </QueryClientProvider>
  );
}

export default App;
