import React, {Component} from "react";
import 'primeflex/primeflex.css'
import 'primereact/resources/themes/arya-orange/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import Stone from "./Stone";
import axios from "axios";
import {GAME_MOVE_URL, GET_GAME_URL, RESPONSE_OK, TOPIC_GAME_STATUS} from "../constants/constants";
import Result from "./Result";
import wsClient from "../ws-client";

class GameBoardTwo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            game: this.props.game
        };
        const websocket = new wsClient();
        websocket.connect();
        websocket.subscribe(TOPIC_GAME_STATUS, this.updateGameStatus.bind(this));

    }

    updateGameStatus(message) {
        let returnMessage = JSON.parse(message.body);
        this.setState({
            game: returnMessage
        })

    }

    sortPieces(gameId, pocketId) {
        axios.post(GAME_MOVE_URL, {
                gameId: gameId,
                pocketId: pocketId
            }
        ).then(res => {
            if (res.status === RESPONSE_OK) {
                this.setState({
                    game: res.data
                })
            }
        }).catch(err => {
            alert(err.response.data.message)
        });
    }

    render() {
        if (this.state.game) {
            if (this.state.game.gameStatus === "OVER") {
                const northStones = this.state.game.board.pockets[13].quantityOfStones;
                const southStones = this.state.game.board.pockets[6].quantityOfStones;
                const winner = northStones > southStones ? "Winner is PLAYER ONE" : "Winner is PLAYER TWO";
                return (
                    <Result winner={winner} northStones={northStones} southStones={southStones}/>
                )
            } else {
                return (
                    <div className="p-megamenu">
                        <h2><span className={this.state.game.playerTurn === "PLAYER_ONE" ? 'badge badge-success' : 'badge badge-secondary'} style={{display: 'block'}}>Player 1</span></h2>
                        <div className="grid">
                            <div className="grid-item grid-item-main-pocket grid-item--width2 grid-item--height2">
                                <Stone stone={this.state.game.board.pockets[13].quantityOfStones}/>
                            </div>
                            <div className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[12].quantityOfStones}/>
                            </div>
                            <div className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[11].quantityOfStones}/>
                            </div>
                            <div className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[10].quantityOfStones}/>
                            </div>
                            <div className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[9].quantityOfStones}/>
                            </div>
                            <div className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[8].quantityOfStones}/>
                            </div>
                            <div className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[7].quantityOfStones}/>
                            </div>


                            <div onClick={() => this.sortPieces(this.state.game.id, 1)} className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[0].quantityOfStones}/>
                            </div>
                            <div onClick={() => this.sortPieces(this.state.game.id, 2)} className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[1].quantityOfStones}/>
                            </div>
                            <div onClick={() => this.sortPieces(this.state.game.id, 3)} className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[2].quantityOfStones}/>
                            </div>
                            <div onClick={() => this.sortPieces(this.state.game.id, 4)} className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[3].quantityOfStones}/>
                            </div>
                            <div onClick={() => this.sortPieces(this.state.game.id, 5)} className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[4].quantityOfStones}/>
                            </div>
                            <div onClick={() => this.sortPieces(this.state.game.id, 6)} className="grid-item grid-item-pocket">
                                <Stone stone={this.state.game.board.pockets[5].quantityOfStones}/>
                            </div>
                            <div className="grid-item grid-item-main-pocket grid-item--width2 grid-item--height2 last-main-pocket">
                                <Stone stone={this.state.game.board.pockets[6].quantityOfStones}/>
                            </div>
                        </div>
                        <h2><span name="player2" className={this.state.game.playerTurn === "PLAYER_TWO" ? 'badge badge-success' : 'badge badge-secondary'} style={{display: 'block'}}>Player 2</span></h2>
                    </div>
                );
            }
        }
        return null;
    }
}

export default GameBoardTwo