import React from 'react';
import 'primeflex/primeflex.css'
import 'primereact/resources/themes/arya-orange/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import Game from "./components/Game.js";
import * as ReactDOM from "react-dom";
import {Button} from "primereact/button";
import {Card} from 'primereact/card';
import axios from "axios";
import {CREATE_GAME_URL} from "/src/main/js/constants/constants";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            game: []
        }

        this.handleStartGame = this.handleStartGame.bind(this);
    }
    componentDidMount() {
        axios.post(CREATE_GAME_URL)
            .then(res => {
                this.setState({
                    game: res.data
                })
            });
    }

    handleStartGame() {
        ReactDOM.render(
            <Game game={this.state.game}/>,
            document.getElementById('root')
        )
    }

    render() {
        return (
            <div className="App">
                <Card title="MANCALA GAME" style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}>
                    <Button className="btn btn-primary btn-lg" onClick={this.handleStartGame}>Start Game</Button>
                </Card>
            </div>
        )
    }
}

export default App;