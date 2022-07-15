<template>
  <q-carousel
    animated
    v-model="slide"
    v-model:fullscreen="fullscreen"
    infinite
    control-color="primary"
    control-type="regular"
    navigation
    padding
    style="min-height: 500px;"
    class="bg-info shadow-2 rounded-borders"
  >

    <q-carousel-slide
      v-for="(imageUrl, index) of artivactDetails.imageUrls" :key="imageUrl" :name="index" :img-src="imageUrl"/>

    <q-carousel-slide
      v-for="(modelUrl, index) of artivactDetails.modelUrls" :key="modelUrl" :name="imageOffset + index">
      <artivact-model-viewer :model-url="artivactDetails.modelUrls[index]"/>
    </q-carousel-slide>

    <template v-slot:navigation-icon="{ index, onClick }">
      <q-btn v-if="index < imageOffset" size="md" icon="image" round dense
             @click="onClick" color="primary"/>
      <q-btn v-if="index >= imageOffset" size="md" icon="3d_rotation" round dense
             @click="onClick" color="primary"/>
    </template>

    <template v-slot:control>
      <q-carousel-control
        position="bottom-right"
        :offset="[15, 22]"
      >
        <q-btn
          round dense color="primary"
          :icon="fullscreen ? 'fullscreen_exit' : 'fullscreen'"
          @click="fullscreen = !fullscreen"
        />
      </q-carousel-control>
    </template>
  </q-carousel>
</template>

<script>
import {ref} from 'vue';
import ArtivactModelViewer from './ArtivactModelViewer';

export default {
  name: 'ArtivactMediaCarousel',
  components: {ArtivactModelViewer},
  props: ['artivactDetails'],
  setup(props) {
    const fullscreen = ref(false)
    let slide = ref(0);
    let imageOffset = ref(0);

    if (props.artivactDetails.imageUrls.length > 0) {
      imageOffset = ref(props.artivactDetails.imageUrls.length);
    }

    return {
      slide,
      imageOffset,
      fullscreen
    };
  }
}
</script>

<style scoped>

model-viewer {
  --poster-color: transparent;
}

</style>
