import logo from './logo.svg';
import './App.css';
import IvblancNavbar from './layouts/IvblancNavbar';
import MyCloset from './pages/MyCloset/MyCloset';

function App() {
  return (
    <div className="App">
      <IvblancNavbar />
      <MyCloset />
    </div>
  );
}

export default App;
