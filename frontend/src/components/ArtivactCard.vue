<template>
  <q-card class="artivact-card">
    <q-img src="https://cdn.quasar.dev/img/parallax2.jpg">
      <div class="absolute-bottom text-h6" v-if="artivactCardData.title">
        {{ artivactCardData.title }}
      </div>
    </q-img>
  </q-card>
</template>

<script lang="ts">
import {ArtivactCardData} from './models';
import {PropType} from 'vue';

export default {
  name: 'ArtivactCard',
  props: {
    artivactCardData: { type: Object as PropType<ArtivactCardData>, required: true}
  }
}
</script>

<style lang="sass" scoped>
.artivact-card
  width: 100%
  max-width: 250px
</style>
