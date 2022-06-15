<template>
  <Header/>
  <div style="display: flex">
    <Aside/>
  </div>
  <router-view style="flex: 1"/>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";

export default {
  components: {Aside, Header}
}

</script>
<style>
</style>