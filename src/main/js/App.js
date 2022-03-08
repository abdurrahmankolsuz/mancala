import React from 'react';
import 'primeflex/primeflex.css'
import 'primereact/resources/themes/arya-orange/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import GameBoardOne from "./components/GameBoardOne.js";
import * as ReactDOM from "react-dom";
import {Button} from "primereact/button";
import axios from "axios";
import {
    CREATE_GAME_URL,
    GET_AVAILABLE_GAME_URL,
    JOIN_GAME_URL,
    RESPONSE_OK, TOPIC_GAME_AVAILABLE_URL,
    TOPIC_GAME_STATUS
} from "/src/main/js/constants/constants";
import {Menubar} from "primereact/menubar";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import GameBoardTwo from "./components/GameBoardTwo";
import { Toast } from 'primereact/toast';
import wsClient from "./ws-client";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            game: null,
            games: []
        }
        this.showWarn = this.showWarn.bind(this);
        this.handleStartGame = this.handleStartGame.bind(this);
        this.createGame = this.createGame.bind(this);
        const websocket = new wsClient();
        websocket.connect();
        websocket.subscribe(TOPIC_GAME_AVAILABLE_URL, this.updateAvailableGames.bind(this));

    }

    showWarn(message) {
        this.toast.show({severity:'warn', summary: 'Warn Message', detail:message, life: 3000});
    }

    updateAvailableGames(message) {
        let returnMessage = JSON.parse(message.body);
        this.setState({
            games: returnMessage
        })

    }

    componentDidMount() {
        this.getAllGames();
    }

    createGame() {
        axios.post(CREATE_GAME_URL)
            .then(res => {
                this.setState(prevState => {
                    return {
                        game: res.data
                    }
                });

            });
    }

    handleStartGame(id) {
        debugger;
        axios.post(JOIN_GAME_URL, {
                gameId: id
            }
        ).then(res => {
            if (res.status === RESPONSE_OK) {
                this.setState({
                    game: res.data
                }, () => {
                    debugger;
                    if (this.state.game.playerTwo) {
                        ReactDOM.render(
                            <GameBoardTwo game={this.state.game}/>,
                            document.getElementById('root')
                        )
                    } else {
                        ReactDOM.render(
                            <GameBoardOne game={this.state.game}/>,
                            document.getElementById('root')
                        )
                    }


                });
            }
        }).catch(err => {
            this.showWarn(err.response.data.message)
        });


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

    actionBodyTemplate = (item) => {
        return <Button className="btn btn-primary btn-lg" onClick={() => this.handleStartGame(item.id)}>Join
            Game</Button>;

    }


    render() {
        const start = <h3>MANCALA (KALAH) GAME</h3>;
        return (
            <div className="App">
                <Toast ref={(el) => this.toast = el} position="bottom-center" />
                <Menubar start={start} end={<Button className="btn btn-primary btn-lg" onClick={this.createGame}>Create
                    Game</Button>}/>
                <div className="p-grid">
                    <div className="p-col">
                        <DataTable value={this.state.games} dataKey="id">
                            <Column field="id" header="Game ID"/>
                            <Column field="gameStatus" header="Game Status"/>
                            <Column field="action" header="Action" headerClassName="sm-invisible"
                                    bodyClassName="sm-invisible" body={this.actionBodyTemplate}></Column>
                        </DataTable>
                    </div>
                </div>
            </div>
        )

    }
}

export default App;