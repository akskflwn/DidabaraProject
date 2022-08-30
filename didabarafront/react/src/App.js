import "./App.css";
import Router from "./Router";
import { RecoilRoot } from "recoil";
import { ThemeProvider } from "styled-components";
import { Theme } from "./Theme";
import { QueryClientProvider, QueryClient } from "react-query";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <RecoilRoot>
        <ThemeProvider theme={Theme}>
          <Router />
        </ThemeProvider>
      </RecoilRoot>
    </QueryClientProvider>
  );
}

export default App;
