<template>
  <img alt="Cloudy logo" src="./assets/cloudy.png" height="300">
  <SpringWeather title="Welcome to this Spring Boot Weather Application" msg=""/>
</template>

<script>
import SpringWeather from './components/SpringWeather.vue'

export default {
  name: 'App',
  components: {
    SpringWeather
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
