import './App.css';
import IvblancNavbar from './layouts/IvblancNavbar';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import routes from './routes';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <Router>
      <div className='App'>
        <IvblancNavbar />
        <Routes>
          {routes.map((route) => {
            return <Route key={route.component} path={route.path} exact element={<route.component />}></Route>;
          })}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
