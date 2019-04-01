/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, TextInput} from 'react-native';
import { Input, ThemeProvider, Button } from 'react-native-elements';
import { listenerCount } from 'cluster';
import { getProducts } from '../API/controllers/productsController';



const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component {

  state = {
    itemNumber: '',
    supplier: '',
    dwp: ''
  }

  onSubmit = () => {
    fetch('http://10.0.2.2:3002/getProduct', {
      method: 'POST',
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(this.state)
    })
    .then(response => response.json())
    .then(data => this.setState({data : data}))
    // .then(products => this.setState({data: this.dwpChoice(products)}))
    .catch(err => console.error(err))

  }

  dwpChoice = products => {
    
    return products.reduce(arr, product => {
      if (arr.find(e => e === product.dwp) ) {
        return product;
      } else {
        arr.push(product.dwp);
        return product;
      }
    }, [])
  }

  render() {
    return (
      <ThemeProvider>
        <Input 
          placeholder='Item number'
          onChangeText={(itemNumber) => this.setState({ itemNumber })} />
        <Input 
          placeholder='Supplier number'
          onChangeText={supplier => this.setState({ supplier })} />
        <Input 
          placeholder='DWP number'
          onChangeText={dwp => this.setState({ dwp })} />

        <Button 
          title='Submit' 
          raised={true}
          type='clear'
          onPress={this.onSubmit}
          />
      </ThemeProvider>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  inputField: {
    width: 90
  }
});
