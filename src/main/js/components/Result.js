import {Card} from "primereact/card";
import React, {Component} from "react";
import {Button} from "primereact/button";
import * as ReactDOM from "react-dom";
import App from "../App";

class Result extends Component {

    constructor(props) {
        super(props);
        this.backToStart = this.backToStart.bind(this);
    }


    backToStart() {
        ReactDOM.render(
            <App/>,
            document.getElementById('root')
        )
    }

    render() {
        const footer = <span><Button label="Back To Start" icon="pi pi-angle-left" onClick={this.backToStart}
                                     style={{marginRight: '.25em'}}/></span>;
        return (
            <Card footer={footer}>
                <div className="flex flex-column">
                    <div
                        className="flex align-items-center justify-content-center"><span>GAME OVER</span>
                    </div>
                    <div
                        className="flex align-items-center justify-content-center"><span>PLAYER ONE STONES</span>
                        : {this.props.northStones}
                    </div>
                    <div
                        className="flex align-items-center justify-content-center">
                        <span> PLAYER TWO STONES : {this.props.southStones}</span>
                    </div>
                    <div
                        className="flex align-items-center justify-content-center"><span> {this.props.winner}</span>
                    </div>
                </div>
            </Card>);
    }

}

export default Result