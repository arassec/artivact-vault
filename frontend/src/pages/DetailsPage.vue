<template>
  <q-page class="row justify-center" v-if="artivactDetails">
    <div class="col-5 q-ma-md items-center">
      <artivact-media-carousel
        v-if="artivactDetails.imageUrls.length > 0 || artivactDetails.modelUrls.length > 0"
        :artivact-details="artivactDetails"/>
    </div>
    <div class="col-5 q-ma-md">
    </div>
  </q-page>
</template>

<script>
import {useQuasar} from 'quasar';
import {api} from '../boot/axios';
import {useRoute} from 'vue-router';
import {onMounted, ref} from 'vue';
import ArtivactMediaCarousel from '../components/ArtivactMediaCarousel';

export default {
  name: 'DetailsPage',
  components: {ArtivactMediaCarousel},
  setup() {
    const $q = useQuasar()
    const data = ref(null)
    const route = useRoute();
    const fullscreen = ref(false)

    function loadData(artivactId) {
      api.get('/api/artivact/' + artivactId)
        .then((response) => {
          data.value = response.data
        })
        .catch(() => {
          $q.notify({
            color: 'negative',
            position: 'top',
            message: 'Loading failed',
            icon: 'report_problem'
          })
        })
    }

    onMounted(() => {
      loadData(route.params.artivactId)
    })

    return {
      artivactDetails: ref(data),
      slide: ref(0),
      fullscreen
    }
  }
}
</script>

<style scoped>

</style>
