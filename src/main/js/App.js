import React from 'react';
import 'primeflex/primeflex.css'
import 'primereact/resources/themes/arya-orange/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import Game from "./components/Game.js";
import * as ReactDOM from "react-dom";
import {Button} from "primereact/button";
import axios from "axios";
import {
    CREATE_GAME_URL,
    GET_AVAILABLE_GAME_URL, GET_GAME_URL,
    RESPONSE_OK,
    TOPIC_GAME_AVAILABLE_URL
} from "/src/main/js/constants/constants";
import {Menubar} from "primereact/menubar";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import wsClient from "./ws-client";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            game: null,
            games: []
        }

        this.handleStartGame = this.handleStartGame.bind(this);
        this.createGame = this.createGame.bind(this);


        // const websocket = new wsClient();
        // websocket.connect();
        // websocket.subscribe(TOPIC_GAME_AVAILABLE_URL, this.updateAvailableGames.bind(this));

    }

    // updateAvailableGames(message) {
    //     let returnMessage = JSON.parse(message.body);
    //     console.log("xxxx "+ returnMessage)
    //     this.setState({
    //         games: returnMessage
    //     })
    //
    // }

    //
    componentDidMount() {
        // this.createGame();
        this.getAllGames();
    }

    createGame() {
        console.log("test")
        axios.post(CREATE_GAME_URL)
            .then(res => {
                this.setState(prevState => {
                    return {
                        games: [...prevState.games, res.data],
                        game: res.data
                    }
                });

            });
    }

    handleStartGame() {
        ReactDOM.render(
            <Game game={this.state.game}/>,
            document.getElementById('root')
        )
    }

    getAllGames() {
        axios.get(GET_AVAILABLE_GAME_URL)
            .then(res => {
                if (res.status === RESPONSE_OK) {
                    this.setState({
                        games: res.data
                    });
                }
            });
    }

    actionBodyTemplate(item) {
            axios.get(GET_GAME_URL, {
                    params: {
                        id: item.id
                    }
                }
            ).then(res => {
                if (res.status === RESPONSE_OK) {
                    this.setState({
                        game: res.data
                    })
                }
            });

        return <Button className="btn btn-primary btn-lg" onClick={this.handleStartGame}>Join
            Game</Button>;

    }


    render() {
        // return (
        //     <div className="App">
        //         <Card title="MANCALA GAME">
        //
        //         </Card>
        //     </div>
        // )

        const start = <h3>MANCALA(KALAH) GAME</h3>;
        return (
            <div className="App">
                <Menubar start={start}
                         end={<Button className="btn btn-primary btn-lg" onClick={this.createGame}>Create
                             Game</Button>}
                />
                <div className="p-grid">
                    <div className="p-col">
                        <DataTable value={this.state.games}
                        >
                            <Column field="id" header="Game ID"/>
                            <Column field="gameStatus" header="Game Status"/>
                            <Column field="action" header="Action" headerClassName="sm-invisible"
                                    bodyClassName="sm-invisible" body={(e) => this.actionBodyTemplate(e)}></Column>
                        </DataTable>
                    </div>
                </div>
            </div>
        )

    }
}

export default App;